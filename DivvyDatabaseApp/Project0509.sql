-- Project0509.sql
-- 9.	The station contains a location as latitude and longitude.  Compute for 
--  each trip the distance covered by that trip, using the following equation to 
--  approximate: sqrt( (69 miles * difference in latitude)^2 + (52 miles * 
--  difference in longitude)^2 ).  For this computation, use the SQRT function and 
--  SQUARE function in SQL.  For the 10 longest trips, return the trip ID, 
--  starting station ID, ending station ID, and distance travelled as Distance.

SELECT TOP 10 TripID, FromStation, ToStation, 
   SQRT( (SQUARE(69 * (S1.Latitude - S2.Latitude))) + (SQUARE(52 * (S1.Longitude - S2.Longitude)))) AS Distance
FROM Trips, Stations AS S1, Stations AS S2
WHERE S1.StationID = FromStation AND S2.StationID = ToStation
ORDER BY Distance DESC;