const API_URL = "http://localhost:8080/api";

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

async function postData(url, body) {
	try {
		const response = await fetch(url, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(body),
		});

		const data = await response.json();

		if (!response.ok) {
			return {
				succes: false,
				error: data.error || `Error ${response.status}`,
			};
		}
		return { succes: true, data };
	} catch (error) {
		console.log("Error", error);
		return { succes: false, error: "Error en la conexion" };
	}
}

async function patchData(url, body) {
	try {
		const response = await fetch(url, {
			method: "PATCH",
			headers: { "Content-Type": "application/json" },
			body: body ? JSON.stringify(body) : undefined,
		});

		const data = await response.json();

		if (!response.ok) {
			return { succes: false, error: data.error || `Error ${response.error}` };
		}

		return { succes: true, data };
	} catch (error) {
		console.log("Error", error);
		return { succes: false, error: "Error en la conexion" };
	}
}

async function crearOrden(vehiculoPlaca, empleadoId) {
	return await postData(`${API_URL}/ordenes`, {
		id_vehiculo: vehiculoPlaca,
		id_mecanico: empleadoId,
	});
}

async function crearServicio(nombre, precio) {
	return await postData(`${API_URL}/servicios`, {
		nombre: nombre,
		precio: precio,
	});
}

async function registrarEmpleado(nombre, telefono, email, rol, contrato) {
	return await postData(`${API_URL}/empleados`, {
		nombre: nombre,
		email: email,
		telefono: telefono,
		rol: rol,
		contrato: contrato,
	});
}

async function registrarCliente(nombre, telefono, email) {
	return await postData(`${API_URL}/clientes`, {
		nombre: nombre,
		email: email,
		telefono: telefono,
	});
}

async function registrarVehiculo(placa, idPropietario, modelo, marca, anio) {
	return await postData(`${API_URL}/vehiculos`, {
		placa: placa,
		id_cliente: idPropietario,
		modelo: modelo,
		marca: marca,
		anio: anio,
	});
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

async function getOrdenesByEstado(estado) {
	return fetchData(`${API_URL}/ordenes?estado=${estado}`);
}

async function getOrdenesByVehiculo(vehiculoPlaca) {
	return fetchData(`${API_URL}/ordenes?placaVehiculo=${vehiculoPlaca}`);
}

async function getVehiculoByPlaca(vehiculoPlaca) {
	const vehicles = await fetchData(`${API_URL}/vehiculos/${vehiculoPlaca}`);

	if (!vehicles || vehicles.length === 0) return null;

	return vehicles[0];
}

async function getVehiculosDeUnCliente(clienteId) {
	return fetchData(`${API_URL}/vehiculos?clienteId=${clienteId}`);
}
async function getOrdenesByServicio(servicioId) {
	//I've to do a specific use case for this, right?
	const ordenes = await getOrdenes();
	if (!ordenes) return [];
	return ordenes.filter((orden) =>
		orden.servicios.some((s) => s.id === servicioId),
	);
}

async function fetchByPrefix(codigo) {
	return fetchData(`${API_URL}/buscar/${codigo}`);
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
