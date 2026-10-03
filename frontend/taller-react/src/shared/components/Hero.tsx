interface HeroProps {
	dashboardRoute?: string;
	inventoryRoute?: string;
	reportsRoute?: string;
}

export function Hero({
	dashboardRoute,
	inventoryRoute,
	reportsRoute,
}: HeroProps) {
	return (
		<>
			<button type="button">AutoService</button>
			<nav>
				<a href={dashboardRoute === undefined ? "#" : dashboardRoute}>
					Dashboard
				</a>
				<a href={inventoryRoute === undefined ? "#" : inventoryRoute}>
					Inventario
				</a>
				<a href={reportsRoute === undefined ? "#" : reportsRoute}>Reportes</a>
			</nav>
			<div>
				<span>Icon</span>
				<span>User Name</span>
			</div>
		</>
	);
}
