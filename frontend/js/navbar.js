const dashboardBtn = document.getElementById("goto-dashboard");
const inventarioBtn = document.getElementById("goto-inventario");
const reportesBtn = document.getElementById("goto-reportes");

function assignNavbarLinks() {
	const isRoot = !window.location.pathname.includes("/pages/");
	const isIndividualPage =
		window.location.pathname.includes("/individual-pages/");

	var prefix;
	var rootPrefix;
	if (isIndividualPage) {
		prefix = "../";
		rootPrefix = "../../";
	} else {
		prefix = isRoot ? "./pages/" : "./";
		rootPrefix = isRoot ? "./" : "../";
	}

	dashboardBtn.href = `${rootPrefix}index.html`;
	inventarioBtn.href = `${prefix}inventario.html`;
	reportesBtn.href = `${prefix}reportes.html`;
}

document.addEventListener("DOMContentLoaded", assignNavbarLinks);
