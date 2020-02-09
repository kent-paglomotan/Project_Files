-- Project0503.sql
-- 3.	For each BikeID, retrieve the number of trips taken with
--  that bike and the total amount of time that bike was checked out.
--  Restrict the results to the top 10 by total time checked out.
SELECT DISTINCT TOP 10 BikeID, COUNT(TripID) AS NumTrips, SUM(TripDuration) AS TimeCheckedOut--COUNT(DISTINCT BikeID) AS NumBikes
FROM Trips
GROUP BY BikeID
ORDER BY TimeCheckedOut DESC;