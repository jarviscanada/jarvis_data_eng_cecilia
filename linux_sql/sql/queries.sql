--Group hosts by hardware
SELECT 
	cpu_number, 
	id AS host_id, 
	total_mem
FROM  
	host_info
ORDER BY
	cpu_number ASC,
	total_mem DESC;


--Average memory usage	
SELECT
	host_usage.host_id AS host_id,
	host_info.hostname AS host_name,
	host_usage.timestamp,
	AVG(((host_info.total_mem - host_usage.memory_free) / host_info.total_mem::float) * 100) AS used_memory_percentage 
FROM 
	host_usage
	INNER JOIN host_info 
	ON host_usage.host_id = host_info.id
GROUP BY
	host_id,
	host_name,
	host_usage.timestamp,
	DATE_TRUNC('hour',host_usage.timestamp) + DATE_PART('minute',host_usage.timestamp)::INT/5 * interval '5 minutes'
ORDER BY
	host_usage.host_id;
