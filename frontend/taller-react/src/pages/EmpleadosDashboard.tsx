import { Dashboard } from "@layouts/Dashboard";

const sidebarItems = [
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

export function EmpleadosDashboard() {
	return (
		<Dashboard
			gradientButton="bg-rose-600 text-white hover:bg-white hover:text-rose-600 hover:border-rose-600"
			gradientClass="bg-linear-to-r from-rose-300 from-20%  to-rose-600"
			items={sidebarItems}
			searchbarPlaceholder="Buscar empleados por ID, Email"
		>
			<h1>Hola</h1>
		</Dashboard>
	);
}
