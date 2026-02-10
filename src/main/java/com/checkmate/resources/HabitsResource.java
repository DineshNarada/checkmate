package com.checkmate.resources;

import com.checkmate.Habit;
import com.checkmate.HabitLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("habits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HabitsResource {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
    
    /**
     * GET /api/habits - Retrieve all habits
     */
    @GET
    public Response getAllHabits() {
        Query query = em.createQuery("SELECT h FROM Habit h");
        List<Habit> habits = query.getResultList();
        return Response.ok(habits).build();
    }
    
    /**
     * POST /api/habits - Create a new habit
     */
    @POST
    @Transactional
    public Response createHabit(Map<String, String> data) {
        String name = data.get("name");
        
        if (name == null || name.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Habit name is required\"}")
                    .build();
        }
        
        Habit habit = new Habit(name);
        em.persist(habit);
        
        return Response.status(Response.Status.CREATED).entity(habit).build();
    }
    
    /**
     * GET /api/habits/{habitId}/logs?month=YYYY-MM - Get logs for a habit in a specific month
     */
    @GET
    @Path("{habitId}/logs")
    public Response getHabitLogs(
            @PathParam("habitId") Long habitId,
            @QueryParam("month") String month) {
        
        Habit habit = em.find(Habit.class, habitId);
        if (habit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Habit not found\"}")
                    .build();
        }
        
        String pattern = month + "%";
        Query query = em.createQuery(
                "SELECT l FROM HabitLog l WHERE l.habit.id = :habitId AND l.date LIKE :pattern");
        query.setParameter("habitId", habitId);
        query.setParameter("pattern", pattern);
        
        List<HabitLog> logs = query.getResultList();
        return Response.ok(logs).build();
    }
    
    /**
     * POST /api/habits/{habitId}/logs - Create or update a habit log
     */
    @POST
    @Path("{habitId}/logs")
    @Transactional
    public Response toggleHabitLog(
            @PathParam("habitId") Long habitId,
            Map<String, Object> data) {
        
        Habit habit = em.find(Habit.class, habitId);
        if (habit == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Habit not found\"}")
                    .build();
        }
        
        String date = (String) data.get("date");
        Integer status = ((Number) data.get("status")).intValue();
        
        if (date == null || date.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Date is required\"}")
                    .build();
        }
        
        // Check if log already exists for this date
        Query findQuery = em.createQuery(
                "SELECT l FROM HabitLog l WHERE l.habit.id = :habitId AND l.date = :date");
        findQuery.setParameter("habitId", habitId);
        findQuery.setParameter("date", date);
        
        List<HabitLog> existingLogs = findQuery.getResultList();
        
        HabitLog log;
        if (!existingLogs.isEmpty()) {
            // Update existing log
            log = existingLogs.get(0);
            log.setStatus(status);
        } else {
            // Create new log
            log = new HabitLog(habit, date, status);
            em.persist(log);
        }
        
        return Response.ok(log).build();
    }
}
