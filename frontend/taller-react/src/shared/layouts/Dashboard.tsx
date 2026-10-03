import { Hero } from "@components/Hero.tsx";
import type { PropsWithChildren } from "react";

export function DashboardLayout({ children }: PropsWithChildren) {
	return (
		<>
			<header className="fixed top-0 flex h-16 w-full items-center gap-8 p-6">
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
