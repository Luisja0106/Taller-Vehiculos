import { TopBar } from "@components/Topbar.tsx";
import type { PropsWithChildren } from "react";

export function Dashboard({ children }: PropsWithChildren) {
	return (
		<>
			<header className="sticky top-0 z-98 flex h-16 w-full items-center gap-8 bg-white p-6 shadow-sm">
				<TopBar dashboardRoute="#" inventoryRoute="#" reportsRoute="#" />
			</header>
			<main>{children}</main>
		</>
	);
}
