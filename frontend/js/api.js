const API_URL = "http://localhost:3002";

async function fetchData(url) {
	try {
		const response = await fetch(url);
		if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
		return await response.json();
	} catch (error) {
		console.error(`Error fetching ${url}:`, error);
		return null;
	}
}

async function getEmpleados() {
	return fetchData(`${API_URL}/empleados`);
}

async function getEmpleado(id) {
	return fetchData(`${API_URL}/empleados/${id}`);
}

async function getOrdenes() {
	return fetchData(`${API_URL}/ordenes`);
}

async function getOrden(id) {
	return fetchData(`${API_URL}/ordenes/${id}`);
}

async function getClientes() {
	return fetchData(`${API_URL}/clientes`);
}

async function getCliente(id) {
	return fetchData(`${API_URL}/clientes/${id}`);
}

async function getVehiculos() {
	return fetchData(`${API_URL}/vehiculos`);
}

async function getVehiculo(id) {
	return fetchData(`${API_URL}/vehiculos/${id}`);
}

async function getServicios() {
	return fetchData(`${API_URL}/servicios`);
}
async function getServicio(id) {
	return fetchData(`${API_URL}/servicios/${id}`);
}

async function getOrdenesByEmpleado(empleadoId) {
	return fetchData(`${API_URL}/ordenes?empleadoId=${empleadoId}`);
}

async function getOrdenesByVehiculo(vehiculoPlaca) {
	return fetchData(`${API_URL}/ordenes?vehiculoPlaca=${vehiculoPlaca}`);
}

async function getVehiculoByPlaca(vehiculoPlaca) {
	const vehicles = await fetchData(
		`${API_URL}/vehiculos?placa=${vehiculoPlaca}`,
	);

	if (!vehicles || vehicles.length === 0) return null;

	return vehicles[0];
}

async function getVehiculosDeUnCliente(clienteId) {
	return fetchData(`${API_URL}/vehiculos?clienteId=${clienteId}`);
}
