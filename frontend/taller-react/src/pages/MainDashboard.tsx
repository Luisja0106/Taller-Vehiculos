import { Filters } from "@components/Filters";
import { Dashboard } from "@layouts/Dashboard";

const sidebarItems = [
	{ label: "Empleados", href: "/empleados" },
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

export function MainDashboard() {
	return (
		<Dashboard
			gradientButton="bg-blue-500 text-white hover:bg-white hover:text-blue-500 hover:border-blue-500"
			gradientClass="bg-linear-to-r from-cyan-400 from-20% via-sky-400 to-blue-500"
			items={sidebarItems}
		>
			<div className="flex flex-col gap-6 p-6">
				<Filters />
			</div>
			<h1>Hola</h1>
		</Dashboard>
	);
}
