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
		PENDIENTE: "badge badge-PENDIENTE",
		EN_PROCESO: "badge badge-EN_PROCESO",
		EN_ESPERA_DE_PAGO: "badge badge-EN_ESPERA",
		FINALIZADO: "badge badge-FINALIZADO",
	};

	return badges[estado] || badges.PENDIENTE;
}

function formatDate(dateRaw) {
	if (!dateRaw) {
		return "sin fecha";
	}
	const [year, month, day] = dateRaw.split("-");

	return `${day}/${month}/${year}`;
}

function getEstadoText(estadoRaw) {
	const estados = {
		PENDIENTE: "Pendiente",
		EN_PROCESO: "En proceso",
		EN_ESPERA_DE_PAGO: "En espera de pago",
		FINALIZADO: "Finalizado",
	};

	return estados[estadoRaw] || estadoRaw;
}

function getRoleText(roleRaw) {
	const role = {
		MECANICO: "Mecanico",
		ADMINISTRADOR: "Administrador",
	};

	return role[roleRaw] || roleRaw;
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
