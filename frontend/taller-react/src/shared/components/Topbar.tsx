import type { MouseEvent } from "react";

interface TopBarProps {
	dashboardRoute: string;
	inventoryRoute: string;
	reportsRoute: string;
	onToggleSideBar?: (newStatus: boolean) => void;
}

export function Topbar({
	dashboardRoute,
	inventoryRoute,
	reportsRoute,
	onToggleSideBar,
}: TopBarProps) {
	const navLinks = [
		{ label: "Dashboard", href: dashboardRoute },
		{ label: "Inventario", href: inventoryRoute },
		{ label: "Reportes", href: reportsRoute },
	];

	const handleOpenSideBar = (event: MouseEvent<HTMLButtonElement>) => {
		event.preventDefault();
		onToggleSideBar?.(true);
	};
	return (
		<>
			<button
				type="button"
				className="cursor-pointer font-bold text-black text-xl"
				onClick={handleOpenSideBar}
			>
				AutoService
			</button>
			<nav className="flex flex-1 items-center justify-center gap-2">
				{navLinks.map((link) => (
					<a
						key={link.label}
						href={link.href}
						className="rounded-md px-2 py-1 font-medium text-gray-500 text-sm transition-(--transition) hover:bg-gray-500/10 hover:text-black"
					>
						{link.label}
					</a>
				))}
			</nav>
			<div className="flex cursor-pointer items-center gap-2">
				<span className="flex size-8 items-center justify-center rounded-full bg-blue-600 font-medium text-white text-xs">
					Icon
				</span>
				<span>User Name</span>
			</div>
		</>
	);
}
