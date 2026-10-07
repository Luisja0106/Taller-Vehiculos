import { FilterSelect } from "@components/FilterSelect";
import { serviceStates } from "@utils/GlobalVar";
import { useState } from "react";

export function Filters() {
	const [isOpen, setIsOpen] = useState(false);
	const toggleOpen = () => {
		setIsOpen(!isOpen);
	};

	const arrowDirection = isOpen ? "rotate-180" : "";
	const display = isOpen ? "grid" : "hidden";
	return (
		<section className="m-6 overflow-hidden rounded-xl border border-slate-300/50 bg-white">
			<button
				type="button"
				onClick={toggleOpen}
				className="flex w-full cursor-pointer select-none items-center justify-between px-6 py-4 font-medium"
			>
				<span>⚙ Filtros</span>
				<span
					className={`text-base transition duration-75 ease-in ${arrowDirection}`}
				>
					▼
				</span>
			</button>
			<div
				className={`${display} grid-cols-3 gap-6 border-slate-300/50 border-t p-6 transition duration-75 ease-in-out`}
				inert={!isOpen}
			>
				<FilterSelect
					id="filter-estado"
					label="Estado"
					placeHolder="Selecione un Estado"
					options={serviceStates}
				/>
				<FilterSelect
					id="filter-mecanicos"
					label="Mecánico"
					placeHolder="Selecione un Mecánico"
					options={[]} //TODO: create the conection with the backend
				/>
				<FilterSelect
					id="filter-vehiculos"
					label="Vehiculo"
					placeHolder="Selecione un Vehiculo"
					options={[]} //TODO: create the conection with the backend
				/>
			</div>
		</section>
	);
}
