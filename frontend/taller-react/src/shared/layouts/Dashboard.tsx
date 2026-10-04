import { Hero } from "@components/Hero.tsx";
import { Overlay } from "@components/Overlay.tsx";
import { Sidebar } from "@components/Sidebar.tsx";
import { Topbar } from "@components/Topbar.tsx";
import { type PropsWithChildren, useState } from "react";

export function Dashboard({ children }: PropsWithChildren) {
	const [isSideBarOpen, setSideBarStatus] = useState(false);
	const [isOveralayVisible, setOveralayVisibility] = useState(false);

	const handleChangeSideBarStatus = (newStatus: boolean) => {
		setOveralayVisibility(newStatus);
		setSideBarStatus(newStatus);
	};

	return (
		<>
			<header className="sticky top-0 z-98 flex h-16 w-full items-center gap-8 bg-white p-6 shadow-sm">
				<Topbar
					dashboardRoute="#"
					inventoryRoute="#"
					reportsRoute="#"
					onToggleSideBar={handleChangeSideBarStatus}
				/>
			</header>
			<main>
				<Sidebar
					onToggleSideBar={handleChangeSideBarStatus}
					style={`fixed top-0 left-0 z-100 flex h-lvh w-2xs flex-col gap-8 bg-white p-6 shadow-2xl transition-transform duration-300 ease-in-out ${isSideBarOpen ? "translate-x-0" : "-translate-x-full"}`}
				/>
				{isOveralayVisible && <Overlay />}
				<Hero
					gradientClasses="bg-linear-to-r from-cyan-400 from-20% via-sky-400 to-blue-500"
					buttonColor="bg-blue-500 text-white hover:bg-white hover:text-blue-500 hover:border-blue-500"
				/>

				{children}
			</main>
		</>
	);
}
