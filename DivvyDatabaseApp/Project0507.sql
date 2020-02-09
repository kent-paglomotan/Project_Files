-- Project0507.sql
-- 7.	For each hour of the day, list how many bikes were checked out during that time.
SELECT DISTINCT StartingHour, COUNT(BikeID) AS NumTrips
FROM Trips
GROUP BY StartingHour
ORDER BY StartingHour ASC;