function getBrandColor(marca) {
	if (!marca) return "var(--brand-default)";
	return `var(--brand-${marca.toLowerCase()})`;
}

function getRoleColor(rol) {
	if (!rol) return "var(--rol-brand-default)";
	return `var(--rol-${rol.toLowerCase()})`;
}

function getBadgeClass(estado) {
	const badges = {
		Pendiente: "badge badge-PENDIENTE",
		"En Proceso": "badge badge-EN_PROCESO",
		"En espera de pago": "badge badge-EN_ESPERA",
		Finalizado: "badge badge-FINALIZADO",
	};

	return badges[estado] || badges.Pendiente;
}

function formatDate(dateRaw) {
	if (!dateRaw) {
		return "sin fecha";
	}
	const [year, month, day] = dateRaw.split("-");

	return `${day}/${month}/${year}`;
}

function getRoleText(roleRaw) {
	const role = {
		MECANICO: "Mecanico",
		ADMINISTRADOR: "Administrador",
	};

	return role[roleRaw] || roleRaw;
}

function getContratoText(contratoRaw) {
	const contrato = {
		FIJO: "Fijo",
		PARCIAL: "Parcial",
		TEMPORAL: "Temporal",
	};

	return contrato[contratoRaw] || contratoRaw;
}

function getMarcaText(marcaRaw) {
	const marca = {
		TOYOTA: "Toyota",
		CHEVROLET: "Chevrolet",
		MAZDA: "Mazda",
		RENAULT: "Renault",
		KIA: "Kia",
	};

	return marca[marcaRaw] || marcaRaw;
}

function getPageByIdPrefix(IdPrefix) {
	const pages = {
		EMP: "empleado.html",
		CLI: "cliente.html",
		VHC: "vehiculo.html",
		ORD: "orden.html",
		SRV: "servicio.html",
	};

	return pages[IdPrefix];
}

function cerrarConfirmarcion() {
	document.getElementById("modal-confirmation").classList.remove("open");
}

function openConfirmation(messaje, onConfirm) {
	const error = document.getElementById("modal-confirmation-error");
	error.textContent = " ";
	error.classList.remove("show");

	document.getElementById("modal-confirmation-messaje").textContent = messaje;
	document.getElementById("modal-confirmation").classList.add("open");

	document.getElementById("modal-confirm-ok").onclick = async () => {
		const resu = await onConfirm();
		if (!resu.success) {
			error.textContent = resu.error;
			error.classList.add("show");
		} else {
			cerrarConfirmarcion();
		}
	};
	document.getElementById("modal-confirm-cancelar").onclick =
		cerrarConfirmarcion;
}

function cerrarInput() {
	document.getElementById("modal-input").classList.remove("open");
}

function openModalInput(label, tipo, onConfirm) {
	const error = document.getElementById("modal-input-error");
	error.textContent = " ";
	error.classList.remove("show");

	document.getElementById("modal-input-label").textContent = label;
	document.getElementById("modal-input-field").type = tipo;
	document.getElementById("modal-input").classList.add("open");

	document.getElementById("modal-input-ok").onclick = async () => {
		const valor = document.getElementById("modal-input-field").value;
		const resu = await onConfirm(valor);

		if (!resu.success) {
			error.textContent = resu.error;
			error.classList.add("show");
		} else {
			cerrarInput();
		}
	};

	document.getElementById("modal-input-cancelar").onclick = cerrarInput;
}

function cerrarSelect() {
	document.getElementById("modal-select").classList.remove("open");
}

function openSelectModal(label, options, onConfirm) {
	const select = document.getElementById("modal-select-field");
	const error = document.getElementById("modal-select-error");
	error.textContent = " ";
	error.classList.remove("show");
	select.value = "";

	document.getElementById("modal-select-label").textContent = label;
	select.innerHTML = "";
	select.add(new Option("Seleccione", "", true));
	options.forEach((op) => {
		select.add(new Option(op.nombre, op.id));
	});

	document.getElementById("modal-select").classList.add("open");

	document.getElementById("modal-select-ok").onclick = async () => {
		const mecanicoId = select.value;
		if (mecanicoId === "") {
			error.textContent = "Debe seleccionar una opcion";
			error.classList.add("show");
			return;
		}
		const resu = await onConfirm(mecanicoId);
		if (!resu.success) {
			error.textContent = resu.error;
			error.classList.add("show");
		} else {
			cerrarSelect();
		}
	};
	document.getElementById("modal-select-cancelar").onclick = cerrarSelect;
}

function openSelectModalVehiculo(label, options, onConfirm) {
	const select = document.getElementById("modal-select-field");
	const error = document.getElementById("modal-select-error");
	error.textContent = " ";
	error.classList.remove("show");
	select.value = "";

	document.getElementById("modal-select-label").textContent = label;
	select.innerHTML = "";
	select.add(new Option("Seleccione", "", true));
	options.forEach((op) => {
		select.add(
			new Option(`${op.marca} ${op.modelo.toLowerCase()} ${op.anio}`, op.placa),
		);
	});

	document.getElementById("modal-select").classList.add("open");

	document.getElementById("modal-select-ok").onclick = async () => {
		const vehiculoPlaca = select.value;
		if (vehiculoPlaca === "") {
			error.textContent = "Debe seleccionar una opcion";
			error.classList.add("show");
			return;
		}
		const resu = await onConfirm(vehiculoPlaca);
		if (!resu.success) {
			error.textContent = resu.error;
			error.classList.add("show");
		} else {
			cerrarSelect();
		}
	};
	document.getElementById("modal-select-cancelar").onclick = cerrarSelect;
}
