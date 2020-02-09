-- Project0502.sql
-- 2.	Retrieve the number of unique bikeIDs
SELECT COUNT(DISTINCT BikeID) AS NumBikes
FROM Trips;