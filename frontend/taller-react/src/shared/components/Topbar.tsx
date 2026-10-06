import { Brand } from "@components/Brand";
import { z_index } from "@utils/GlobalVar";
import { Link } from "@utils/Link.tsx";

interface TopBarProps {
	dashboardRoute: string;
	inventoryRoute: string;
	reportsRoute: string;
	onMenuClick: () => void;
}

export function Topbar({
	dashboardRoute,
	inventoryRoute,
	reportsRoute,
	onMenuClick,
}: TopBarProps) {
	const navLinks = [
		{ label: "Dashboard", href: dashboardRoute },
		{ label: "Inventario", href: inventoryRoute },
		{ label: "Reportes", href: reportsRoute },
	];
	return (
		<header
			className={`sticky top-0 ${z_index.Topbar} flex h-16 w-full items-center gap-8 bg-white p-6 shadow-sm`}
		>
			<button type="button" className="cursor-pointer" onClick={onMenuClick}>
				<Brand />
			</button>
			<nav className="flex flex-1 items-center justify-center gap-2">
				{navLinks.map((link) => (
					<Link
						key={link.label}
						href={link.href}
						className="rounded-md px-2 py-1 font-medium text-gray-500 text-sm transition-(--transition) hover:bg-gray-500/10 hover:text-black"
					>
						{link.label}
					</Link>
				))}
			</nav>
			<div className="flex cursor-pointer items-center gap-2">
				<span className="flex size-8 items-center justify-center rounded-full bg-blue-600 font-medium text-white text-xs">
					Icon
				</span>
				<span>User Name</span>
			</div>
		</header>
	);
}
