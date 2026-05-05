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

openBtn.addEventListener("click", openDrawner); //without () because using them means that call the funcition instantly
closeBtn.addEventListener("click", closeDrawner);
overlay.addEventListener("click", closeDrawner);
