const estadoFilter = document.getElementById("filter-estado");
const mecanicoFilter = document.getElementById("filter-mecanico");
const vehiculoFilter = document.getElementById("filter-vehiculo");

async function setFilterParameters() {
	const mecanicos = await getMecanicos();

	mecanicos.forEach((mecanico) => {
		const optionMecanico = new Option(mecanico.nombre, mecanico.id);
		mecanicoFilter.add(optionMecanico);
	});

	const vehiculos = await getVehiculos();

	vehiculos.forEach((vehiculo) => {
		const optionVehiculo = new Option(
			`${getMarcaText(vehiculo.marca)} ${vehiculo.modelo} ${vehiculo.anio}`,
			vehiculo.placa,
		);
		vehiculoFilter.add(optionVehiculo);
	});
}

async function applyFilters() {
	const estado = estadoFilter.value;
	const empleado = mecanicoFilter.value;
	const vehiculo = vehiculoFilter.value;

	const ordenes = await getOrdenesWithFilters(estado, empleado, vehiculo);

	renderCards(ordenes);
}

document.addEventListener("DOMContentLoaded", setFilterParameters);
estadoFilter.addEventListener("change", applyFilters);
mecanicoFilter.addEventListener("change", applyFilters);
vehiculoFilter.addEventListener("change", applyFilters);
