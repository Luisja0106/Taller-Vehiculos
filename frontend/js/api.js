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

async function crearOrden(vehiculoPlaca, empleadoId) {
	try {
		const response = await fetch(`${API_URL}/ordenes`, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({
				vehiculoPlaca: vehiculoPlaca,
				empleadoId: empleadoId,
			}),
		});
		if (!response.ok)
			throw new Error(`Error al crear orden: ${response.status}`);
		return await response.json();
	} catch (error) {
		console.error("Error:", error);
		return null;
	}
}

async function registrarEmpleado(nombre, telefono, email, rol, contrato) {
	try {
		const response = await fetch(`${API_URL}/empleados`, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({
				nombre: nombre,
				email: email,
				telefono: telefono,
				rol: rol,
				contrato: contrato,
			}),
		});
		if (!response.ok)
			throw new Error(`Error al crear empleado: ${response.status}`);
		return await response.json();
	} catch (error) {
		console.error("Error:", error);
		return null;
	}
}

async function registrarCliente(nombre, telefono, email) {
	try {
		const response = await fetch(`${API_URL}/clientes`, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({
				nombre: nombre,
				email: email,
				telefono: telefono,
			}),
		});
		if (!response.ok)
			throw new Error(`Error al crear cliente: ${response.status}`);
		return await response.json();
	} catch (error) {
		console.error("Error:", error);
		return null;
	}
}

async function registrarVehiculo(placa, idPropietario, modelo, marca, anio) {
	try {
		const response = await fetch(`${API_URL}/vehiculos`, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({
				placa: placa,
				modelo: modelo,
				marca: marca,
				anio: anio,
				clienteId: idPropietario,
			}),
		});
		if (!response.ok)
			throw new Error(`Error al crear vehiculo: ${response.status}`);
		return await response.json();
	} catch (error) {
		console.error("Error:", error);
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
async function getOrdenesByServicio(servicioId) {
	const ordenes = await getOrdenes();
	if (!ordenes) return [];
	return ordenes.filter((orden) =>
		orden.servicios.some((s) => s.id === servicioId),
	);
}

async function fetchByPrefix(prefix, codigo) {
	switch (prefix) {
		case "EMP":
			return await getEmpleado(codigo);
		case "CLI":
			return await getCliente(codigo);
		case "ORD":
			return await getOrden(codigo);
		case "SRV":
			return await getServicio(codigo);
		case "VHC":
			return await getVehiculo(codigo);
		default:
			return null;
	}
}

async function getMecanicos() {
	return fetchData(`${API_URL}/empleados?rol=MECANICO`);
}

async function getOrdenesWithFilters(estado, empleadoId, vehiculoPlaca) {
	let url = `${API_URL}/ordenes?`;
	if (estado) url += `estado=${estado}&`;
	if (empleadoId) url += `empleadoId=${empleadoId}&`;
	if (vehiculoPlaca) url += `vehiculoPlaca=${vehiculoPlaca}&`;
	return fetchData(url);
}
