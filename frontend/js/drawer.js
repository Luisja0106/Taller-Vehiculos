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

function assignLinks() {
	const mecanicosBtn = document.getElementById("goto-mecanicos");
	const vehiculosBtn = document.getElementById("goto-vehiculos");
	const clientesBtn = document.getElementById("goto-clientes");
	const serviciosBtn = document.getElementById("goto-servicios");

	const isRoot = !window.location.pathname.includes("/pages/");

	const prefix = isRoot ? "./pages/" : "./";

	mecanicosBtn.href = `${prefix}mecanico.html`;
	vehiculosBtn.href = `${prefix}vehiculo.html`;
	clientesBtn.href = `${prefix}cliente.html`;
	serviciosBtn.href = `${prefix}servicios.html`;
}

openBtn.addEventListener("click", openDrawner); //without () because using them means that call the funcition instantly
closeBtn.addEventListener("click", closeDrawner);
overlay.addEventListener("click", closeDrawner);
document.addEventListener("DOMContentLoaded", assignLinks);
