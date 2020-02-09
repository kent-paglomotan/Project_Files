-- Project0504.sql
-- 4.	For each StationID, retrieve the number of trips taken 
-- from that station and to that station (as separate values).
SELECT TOP 10 Stations.StationID, 
    (SELECT COUNT(*) FROM Trips AS S1 WHERE Stations.StationID = S1.FromStation) AS NumTripsFrom,
    (SELECT COUNT(*) FROM Trips AS S2 WHERE Stations.StationID = S2.ToStation) AS NumTripsTo
FROM Stations
ORDER BY 
    ((SELECT COUNT(*) FROM Trips AS TOTAL1 WHERE Stations.StationID = TOTAL1.FromStation) +
    (SELECT COUNT(*) FROM Trips AS TOTAL2 WHERE Stations.StationID = TOTAL2.ToStation)) DESC, Stations.StationID ASC;