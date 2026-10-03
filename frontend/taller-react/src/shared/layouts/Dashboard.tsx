import { Hero } from "@components/Hero.tsx";
import type { PropsWithChildren } from "react";

export function DashboardLayout({ children }: PropsWithChildren) {
	return (
		<>
			<header className="p-6 flex fixed top-0 w-full gap-8 h-16 items-center">
				<Hero
					dashboardRoute={undefined}
					inventoryRoute={undefined}
					reportsRoute={undefined}
				/>
			</header>
			{children}
		</>
	);
}
