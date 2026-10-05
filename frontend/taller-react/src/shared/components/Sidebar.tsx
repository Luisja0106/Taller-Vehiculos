const mockOptions = [
	{ label: "Empleados", href: "#" },
	{ label: "Vehiculos", href: "#" },
	{ label: "Clientes", href: "#" },
	{ label: "Servicios", href: "#" },
];

interface SideBarProps {
	isOpen: boolean;
	onClose: () => void;
}

export function Sidebar({ isOpen, onClose }: SideBarProps) {
	const positionClasses = isOpen ? "translate-x-0" : "-translate-x-full";

	return (
		<aside
			className={`fixed top-0 left-0 z-100 flex h-lvh w-2xs flex-col gap-8 bg-white p-6 shadow-2xl transition-transform duration-300 ease-in-out ${positionClasses}`}
		>
			<div className="flex items-center justify-between">
				<span className="text-nowrap font-bold text-black text-xl">
					AutoService
				</span>
				<button
					type="button"
					className="cursor-pointer text-nowrap border-transparent bg-transparent text-xl"
					aria-label="close-sidebar"
					onClick={onClose}
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
	);
}
