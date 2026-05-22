const nuevoClienteBtn = document.getElementById("nuevo-cliente-button");
const cerrarClienteBtn = document.getElementById("modal-close");
const cancelModalBtn = document.getElementById("modal-cancel");
const confirmModalBtn = document.getElementById("modal-confirm");

const modalOverlay = document.getElementById("modal-overlay");
const modalNombre = document.getElementById("modal-nombre");
const modalTelefono = document.getElementById("modal-telefono");
const modalEmail = document.getElementById("modal-email");

function openModalOverlay() {
	modalOverlay.classList.add("open");
}

function cancelarCreation() {
	const modalError = document.getElementById("modal-error");

	modalNombre.value = "";
	modalTelefono.value = "";
	modalEmail.value = "";
	modalError.style.display = "none";

	closeModalOverlay();
}

function closeModalOverlay() {
	modalOverlay.classList.remove("open");
}

async function confirmarCreacion() {
	const nombreCliente = modalNombre.value;
	const telefonoCliente = modalTelefono.value;
	const emailCliente = modalEmail.value;
	const modalError = document.getElementById("modal-error");

	if (!nombreCliente || !telefonoCliente || !emailCliente) {
		modalError.textContent = "Llene todos los datos";
		modalError.style.display = "block";
		return;
	}

	const nuevoCliente = await registrarCliente(
		nombreCliente,
		telefonoCliente,
		emailCliente,
	);
	if (!nuevoCliente.success) {
		modalError.textContent = nuevoCliente.error;
		modalError.style.display = "block";
		return;
	}

	closeModalOverlay();
	window.location.href = `./individual-pages/cliente.html?id=${nuevoCliente.data.id}`;
}

confirmModalBtn.addEventListener("click", confirmarCreacion);
nuevoClienteBtn.addEventListener("click", openModalOverlay);
cancelModalBtn.addEventListener("click", cancelarCreation);
cerrarClienteBtn.addEventListener("click", closeModalOverlay);
