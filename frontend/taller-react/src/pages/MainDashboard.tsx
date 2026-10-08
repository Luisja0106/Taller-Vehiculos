import { CardsGrid } from "@components/CardsGrid";
import { CreatebuttonEntity } from "@components/CreateButtonEntity";
import { Filters } from "@components/Filters";
import { Hero } from "@components/Hero";
import { Searchbar } from "@components/Searchbar";
import { Dashboard } from "@layouts/Dashboard";
import { NavigateTo } from "@utils/NavigateTo";
import { type ChangeEvent, useId } from "react";

const sidebarItems = [
	{ label: "Empleados", href: "/empleados" },
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

export function MainDashboard() {
	const searchId = useId();
	const idEstado = useId();
	const idMecanico = useId();
	const idVehiculo = useId();
	const handleCreateEntity = () => console.log("hello world");
	const handleSearch = (event: ChangeEvent<HTMLFormElement>) => {
		event.preventDefault();
		const formData = new FormData(event.currentTarget);
		const searchTerm = (formData.get(searchId) as string).trim();

		const url = searchTerm
			? `/search?q=${encodeURIComponent(searchTerm)}`
			: "/search";

		NavigateTo(url);
	};
	return (
		<Dashboard theme="blue" items={sidebarItems}>
			<Hero>
				<Searchbar
					onSubmit={handleSearch}
					name={searchId}
					placeholder="Buscar por ID: Vehiculo, Empleado, Etc..."
				/>
			</Hero>
			<div className="flex flex-col gap-6 p-6">
				<Filters
					idEstado={idEstado}
					idMecanico={idMecanico}
					idVehiculo={idVehiculo}
				/>
			</div>
			<div className="px-6 py-0">
				<CreatebuttonEntity value="Nueva Orden" onClick={handleCreateEntity} />
			</div>
			<CardsGrid maxItems={2} />
		</Dashboard>
	);
}
