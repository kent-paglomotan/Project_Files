-- Project0508.sql
-- 8.	For each hour of the day, list the percentage of bikes checked out during --  that hour relative to the other hours of the day.
SELECT StartingHour, (COUNT(BikeID) * 100.0/ (SELECT COUNT(*) FROM Trips)) AS Percentage
FROM Trips
--WHERE StartingHour = BikeID
GROUP BY StartingHour