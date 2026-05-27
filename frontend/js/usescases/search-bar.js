const form = document.getElementById("search-form");
const input = document.getElementById("search-input");
const error = document.getElementById("search-error");

function showError(message) {
	error.textContent = message;

	error.classList.add("show");
}

function hideError() {
	error.classList.remove("show");
}

async function handleSearch(event) {
	event.preventDefault(); //prevent pages reload on form submit

	const codigo = input.value.trim().toUpperCase();

	if (!codigo) {
		hideError();
		return;
	}

	hideError();

	const prefix = codigo.substring(0, 3).toUpperCase();

	const validPrefix = ["EMP", "CLI", "VHC", "ORD", "SRV"];

	if (!validPrefix.includes(prefix)) {
		showError("Error, prefijo de codigo desconocido");
		return;
	}
	hideError();

	const encontrado = await fetchByPrefix(codigo);

	if (!encontrado) {
		showError("No se encontro ningun resultado para ese codigo");
		return;
	}

	const page = getPageByIdPrefix(prefix);
	const isRoot = !window.location.pathname.includes("/pages/");
	const base = isRoot ? "./pages/individual-pages/" : "./individual-pages/";

	if (page === "vehiculo.html") {
		window.location.href = `${base}${page}?placa=${encontrado.placa}`;
	} else {
		window.location.href = `${base}${page}?id=${encontrado.id}`;
	}
}

form.addEventListener("submit", handleSearch);
