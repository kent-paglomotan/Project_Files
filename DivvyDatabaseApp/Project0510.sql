-- Project0510.sql
-- 10.	Compute for each trip the average speed of the bicyclist, by taking the 
--  distance travelled computed in the previous question (which is in miles) and 
--  divide by the length in hours (the length is stored in seconds).  For the 10 
--  fastest trips, return the trip ID, bike ID, and the speed as mph.
SELECT TOP 10 TripID, BikeID,
   (SQRT( (SQUARE(69 * (S1.Latitude - S2.Latitude))) + (SQUARE(52 * (S1.Longitude - S2.Longitude)))) / (TripDuration/3600.0)) AS mph
FROM Trips, Stations AS S1, Stations AS S2
WHERE S1.StationID = FromStation AND S2.StationID = ToStation
ORDER BY mph DESC;