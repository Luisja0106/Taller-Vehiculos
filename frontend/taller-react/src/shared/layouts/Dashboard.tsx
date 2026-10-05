import { Hero } from "@components/Hero.tsx";
import { Overlay } from "@components/Overlay.tsx";
import { Sidebar, type sidebarOptions } from "@components/Sidebar.tsx";
import { Topbar } from "@components/Topbar.tsx";
import { type ReactNode, useState } from "react";

interface DashboardProps {
	children: ReactNode;
	gradientClass: string;
	gradientButton: string;
	searchbarPlaceholder?: string;
	items: sidebarOptions[];
}

export function Dashboard({
	children,
	gradientClass,
	gradientButton,
	items,
	searchbarPlaceholder = "Buscar por ID, Vehiculo, Empleado",
}: DashboardProps) {
	const [isSideBarOpen, setSideBarStatus] = useState(false);

	const openSidebar = () => setSideBarStatus(true);
	const closeSidebar = () => setSideBarStatus(false);

	return (
		<>
			<Topbar
				dashboardRoute="/"
				inventoryRoute="#"
				reportsRoute="#"
				onMenuClick={openSidebar}
			/>
			<Sidebar isOpen={isSideBarOpen} onClose={closeSidebar} items={items} />
			{isSideBarOpen && <Overlay onClick={closeSidebar} />}
			<main>
				<Hero
					gradientClasses={gradientClass}
					buttonColor={gradientButton}
					searchPlaceHolder={searchbarPlaceholder}
				/>

				{children}
			</main>
		</>
	);
}
