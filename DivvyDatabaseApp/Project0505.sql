-- Project0505.sql
-- 5.	For each customer list the number of trips they have taken.
--   Restrict the results to the 10 users who have taken the most trips.
SELECT DISTINCT TOP 10 UserID, COUNT(TripID) AS NumTrips
FROM Trips
GROUP BY UserID
ORDER BY NumTrips DESC, UserID ASC