const modal = document.getElementById("ordenes-finalizadas-modal");

async function initPage() {
	await renderOrdenesFinalizadas();
	setUpVerServiciosPasadosButton();
}

function setUpVerServiciosPasadosButton() {
	const btnOpen = document.getElementById("ver-ordenes-pasadas-btn");
	const btnClose = document.getElementById("close-ordenes-finalizadas-btn");
	btnOpen.addEventListener("click", openModal);
	btnClose.addEventListener("click", closeModal);
}

async function renderOrdenesFinalizadas() {
	const empleado =
		new URLSearchParams(window.location.search).get("id") || null;
	const vehiculo =
		new URLSearchParams(window.location.search).get("placa") || null;
	const ordenes = await getOrdenesWithFilters("FINALIZADO", empleado, vehiculo);
	const container = document.getElementById("ordenes-finalizadas-list");

	if (ordenes.length === 0 || ordenes === null) {
		container.innerHTML =
			"<p id='ordenes-finalizadas-fallback'>No hay órdenes finalizadas.</p>";
		return;
	}
	container.innerHTML = "";

	if (empleado) {
		ordenes.forEach((orden) => {
			const ordenHtml = createEmpleadoHtml(orden);
			container.insertAdjacentHTML("beforeend", ordenHtml);
		});
	} else if (vehiculo) {
		ordenes.forEach((orden) => {
			const ordenHtml = createVehiculoHtml(orden);
			container.insertAdjacentHTML("beforeend", ordenHtml);
		});
	}
}

function createEmpleadoHtml(orden) {
	return `
    <li class='orden-finalizada-box'>
    <a class='ordenes-finalizado-ordenId' href='orden.html?id=${orden.id}'>${orden.id}</a>
    <a class='ordenes-finalizado-vehiculoPlaca' href='vehiculo.html?placa=${orden.vehiculoPlaca}'>${orden.vehiculoPlaca}</a>
    <h5 class='ordenes-finalizado-valor'>$${orden.valorVenta}</h5>
    </li>
    `;
}

function createVehiculoHtml(orden) {
	return `
    <li class='orden-finalizada-box'>
    <a class='ordenes-finalizado-ordenId' href='orden.html?id=${orden.id}'>${orden.id}</a>
    <a class='ordenes-finalizado-vehiculoPlaca' href='empleado.html?id=${orden.empleadoId}'>${orden.empleadoNombre}</a>
    <h5 class='ordenes-finalizado-valor'>$${orden.valorVenta}</h5>
    </li>
    `;
}

function openModal() {
	modal.classList.add("open");
}

function closeModal() {
	modal.classList.remove("open");
}

document.addEventListener("DOMContentLoaded", initPage);
