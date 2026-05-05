async function initOrden() {
	const params = new URLSearchParams(window.location.search);
	const ordenId = params.get("id"); //the url is orden.html?id=... so will get the id part

	if (!ordenId) {
		//no id in the URL return to the dashboard
		window.location.href = "../index.html";
		return;
	}

	const orden = await getOrden(ordenId);

	if (!orden) {
		window.location.href = "../index.html";
		return;
	}

	document.title = `Orden ${orden.id} - AutoService`;

	document.getElementById("orden-id").textContent = orden.id;

	document.getElementById("orden-vehiculo").textContent =
		`${orden.vehiculoModelo} ${orden.vehiculoAnio}`;

	const brandColor = getBrandColor(orden.vehiculoMarca);
	document.getElementById("hero").style.background = brandColor;

	const mecanicoElement = document.getElementById("orden-mecanico");
	mecanicoElement.textContent = orden.empleadoNombre;
	mecanicoElement.href = `mecanico.html?id=${orden.empleadoId}`;

	const clienteElement = document.getElementById("orden-cliente");
	clienteElement.textContent = orden.clienteNombre;
	clienteElement.href = `cliente.html?id=${orden.clienteId}`;

	document.getElementById("orden-estado").textContent = getEstadoText(
		orden.estado,
	);
	document.getElementById("orden-estado").className =
		`info-estado ${getBadgeClass(orden.estado)}`;

	document.getElementById("orden-fecha").textContent = orden.fechaEntrada;
	renderServicios(orden.servicios);
}

function renderServicios(servicios) {
	const container = document.getElementById("servicios-lista");

	if (!servicios || servicios.length === 0) {
		container.innerHTML += "<p>No hay servicios aplicados </p>";
		return;
	}

	servicios.forEach((servicio) => {
		container.innerHTML += `
    <div class="servicios-box">
      <span class="servicio-name">${servicio.nombre}</span>
      <button type="button">Remover</button>
    </div>
    `;
	});
}

document.addEventListener("DOMContentLoaded", initOrden);
