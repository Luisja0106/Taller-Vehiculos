import { CreatebuttonEntity } from "@components/CreateButtonEntity";
import { Hero } from "@components/Hero";
import { Searchbar } from "@components/Searchbar";
import { Dashboard } from "@layouts/Dashboard";
import { NavigateTo } from "@utils/NavigateTo";
import { type ChangeEvent, useId } from "react";

const sidebarItems = [
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

export function EmpleadosDashboard() {
	const idSearch = useId();
	const handleSearch = (event: ChangeEvent<HTMLFormElement>) => {
		event.preventDefault();
		const formData = new FormData(event.currentTarget);
		const searchTerm = (formData.get(idSearch) as string).trim();

		const url = searchTerm
			? `/search?q=${encodeURIComponent(searchTerm)}`
			: "/search";

		NavigateTo(url);
	};

	const handleCreateEntity = () => console.log("Hello world");
	return (
		<Dashboard items={sidebarItems} theme="rose">
			<Hero>
				<Searchbar
					name={idSearch}
					onSubmit={handleSearch}
					placeholder="Buscar Empleado por ID, Email"
				/>
			</Hero>
			<div className="px-4 py-6">
				<CreatebuttonEntity
					value="Nuevo Empleado"
					onClick={handleCreateEntity}
				/>
			</div>
			<h1>Hola</h1>
		</Dashboard>
	);
}
