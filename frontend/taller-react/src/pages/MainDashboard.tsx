import { Filters } from "@components/Filters";
import { Hero } from "@components/Hero";
import { Searchbar } from "@components/Searchbar";
import { Dashboard } from "@layouts/Dashboard";

const sidebarItems = [
	{ label: "Empleados", href: "/empleados" },
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

export function MainDashboard() {
	return (
		<Dashboard items={sidebarItems}>
			<Hero gradientClasses="bg-blue-500">
				<Searchbar
					buttonColor="bg-blue-500"
					placeholder="Buscar por ID: Vehiculo, Empleado, Etc..."
				/>
			</Hero>
			<div className="flex flex-col gap-6 p-6">
				<Filters />
			</div>
			<h1>Hola</h1>
		</Dashboard>
	);
}
