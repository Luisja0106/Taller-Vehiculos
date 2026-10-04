import { Hero } from "@components/Hero.tsx";
import { Sidebar } from "@components/Sidebar";
import { Topbar } from "@components/Topbar.tsx";
import type { PropsWithChildren } from "react";

export function Dashboard({ children }: PropsWithChildren) {
	return (
		<>
			<header className="sticky top-0 z-98 flex h-16 w-full items-center gap-8 bg-white p-6 shadow-sm">
				<Topbar dashboardRoute="#" inventoryRoute="#" reportsRoute="#" />
			</header>
			<main>
				{/* <Sidebar /> */}
				<Hero
					gradientClasses="bg-linear-to-r from-cyan-400 from-20% via-sky-400 to-blue-500"
					buttonColor="bg-blue-500 text-white hover:bg-white hover:text-blue-500 hover:border-blue-500"
				/>

				{children}
			</main>
		</>
	);
}
