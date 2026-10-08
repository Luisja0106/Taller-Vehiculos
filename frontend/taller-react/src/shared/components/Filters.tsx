import { FilterSelect } from "@components/FilterSelect.tsx";
import { useDisclosure } from "@hooks/useDisclosure";
import { serviceStates } from "@utils/GlobalVar.ts";

interface FilterProps {
	idMecanico: string;
	idEstado: string;
	idVehiculo: string;
}

export function Filters({ idEstado, idMecanico, idVehiculo }: FilterProps) {
	const { isOpen, toggle } = useDisclosure();
	const arrowDirection = isOpen ? "rotate-180" : "";
	const panelRows = isOpen ? "grid-rows-[1fr]" : "grid-rows-[0fr]";

	return (
		<section className="overflow-hidden rounded-xl border border-slate-300/50 bg-white">
			<button
				type="button"
				onClick={toggle}
				aria-expanded={isOpen}
				aria-controls="filters-panel"
				className="flex w-full cursor-pointer select-none items-center justify-between px-6 py-4 font-medium"
			>
				<span>Filtros</span>
				<span
					aria-hidden="true"
					className={`text-base transition duration-300 ease-in ${arrowDirection}`}
				>
					▼
				</span>
			</button>
			<div
				className={`grid transition-[grid-template-rows] duration-300 ease-in-out ${panelRows}`}
				inert={!isOpen}
			>
				<div className="overflow-hidden, min-h-0">
					<div className="grid grid-cols-3 gap-6 border-slate-300/50 border-t p-6">
						<FilterSelect
							name={idEstado}
							id="filter-estado"
							label="Estado"
							placeHolder="Seleccione un Estado"
							options={serviceStates}
						/>
						<FilterSelect
							id="filter-mecanicos"
							name={idMecanico}
							label="Mecánico"
							placeHolder="Seleccione un Mecánico"
							options={[]} //TODO: create the conection with the backend
						/>
						<FilterSelect
							name={idVehiculo}
							id="filter-vehiculos"
							label="Vehiculo"
							placeHolder="Seleccione un Vehículo"
							options={[]} //TODO: create the conection with the backend
						/>
					</div>
				</div>
			</div>
		</section>
	);
}
