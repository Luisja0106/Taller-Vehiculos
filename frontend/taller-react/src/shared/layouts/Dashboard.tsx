import { Hero } from "@components/Hero.tsx";
import { Overlay } from "@components/Overlay.tsx";
import { Sidebar, type sidebarOptions } from "@components/Sidebar.tsx";
import { Topbar } from "@components/Topbar.tsx";
import { useDisclosure } from "@hooks/useDisclosure";
import type { ReactNode } from "react";

interface DashboardProps {
	children: ReactNode;
	gradientClass: string; //TODO: Fix this mechanism for the styles
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
	const { isOpen, open, close } = useDisclosure();

	return (
		<>
			<Topbar
				dashboardRoute="/"
				inventoryRoute="#"
				reportsRoute="#"
				onMenuClick={open}
			/>
			<Sidebar isOpen={isOpen} onClose={close} items={items} />
			<Overlay isVisible={isOpen} onClick={close} />
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
