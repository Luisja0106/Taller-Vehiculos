import { Overlay } from "@components/Overlay.tsx";
import type { MouseEvent } from "react";

const mockOptions = [
	{ label: "Empleados", href: "#" },
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

interface SideBarProps {
	onToggleSideBar?: (newStatus: boolean) => void;
	style: string;
}

export function Sidebar({ style, onToggleSideBar }: SideBarProps) {
	const handleCloseSideBar = (event: MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
		onToggleSideBar?.(false);
	};

	return (
		<>
			<aside className={style}>
				<div className="flex items-center justify-between">
					<span className="text-nowrap font-bold text-black text-xl">
						AutoService
					</span>
					<button
						type="button"
						className="cursor-pointer text-nowrap border-transparent bg-transparent text-xl"
						onClick={handleCloseSideBar}
					>
						X
					</button>
				</div>
				<ul className="ml-2 flex flex-col gap-8">
					{mockOptions.map((option) => (
						<li key={option.label}>
							<a
								href={option.href}
								className="block rounded-md p-2 font-medium text-base text-gray-400 transition-(--transition) hover:bg-gray-400/10 hover:text-black"
							>
								{option.label}
							</a>
						</li>
					))}
				</ul>
			</aside>
		</>
	);
}
