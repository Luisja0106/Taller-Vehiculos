import { Hero } from "@components/Hero";
import { Searchbar } from "@components/Searchbar";
import { Dashboard } from "@layouts/Dashboard";

const sidebarItems = [
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

export function EmpleadosDashboard() {
	return (
		<Dashboard items={sidebarItems} theme="rose">
			<Hero>
				<Searchbar placeholder="Buscar Empleado por ID, Email" />
			</Hero>
			<h1>Hola</h1>
		</Dashboard>
	);
}
