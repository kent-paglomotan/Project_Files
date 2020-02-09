-- Project0506.sql
-- 6.	For each age group (year) among customers, list the average 
--  ride duration among all trips customers of that age group took.  
SELECT DISTINCT TOP 10 (YEAR(GETDATE()) - BirthYear) AS Age, AVG(TripDuration) AS AverageTripDurationPerAgeGroup
FROM Trips
INNER JOIN Users
    ON Trips.UserID = Users.UserID
GROUP BY BirthYear
ORDER BY AverageTripDurationPerAgeGroup DESC;
