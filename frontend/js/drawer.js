const drawer = document.getElementById("drawer");
const overlay = document.getElementById("overlay");
const openBtn = document.getElementById("open-drawer");
const closeBtn = document.getElementById("close-drawer");

function openDrawner() {
	drawer.classList.add("open"); //using the css event
	overlay.classList.add("visible");
}
function closeDrawner() {
	drawer.classList.remove("open");
	overlay.classList.remove("visible");
}

function assignDrawerLinks() {
	const empleadosBtn = document.getElementById("goto-empleados");
	const vehiculosBtn = document.getElementById("goto-vehiculos");
	const clientesBtn = document.getElementById("goto-clientes");
	const serviciosBtn = document.getElementById("goto-servicios");

	const isRoot = !window.location.pathname.includes("/pages/");
	const isIndividualPage =
		window.location.pathname.includes("/individual-pages/");

	var prefix;

	if (isIndividualPage) {
		prefix = "../";
	} else {
		prefix = isRoot ? "./pages/" : "./";
	}

	empleadosBtn.href = `${prefix}empleados.html`;
	vehiculosBtn.href = `${prefix}vehiculos.html`;
	clientesBtn.href = `${prefix}clientes.html`;
	serviciosBtn.href = `${prefix}servicios.html`;
}

openBtn.addEventListener("click", openDrawner); //without () because using them means that call the funcition instantly
closeBtn.addEventListener("click", closeDrawner);
overlay.addEventListener("click", closeDrawner);
document.addEventListener("DOMContentLoaded", assignDrawerLinks);
