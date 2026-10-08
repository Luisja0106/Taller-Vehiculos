import { Overlay } from "@components/Overlay.tsx";
import { Sidebar, type sidebarOptions } from "@components/Sidebar.tsx";
import { Topbar } from "@components/Topbar.tsx";
import { useDisclosure } from "@hooks/useDisclosure";
import type { Theme } from "@utils/Theme";
import type { ReactNode } from "react";

interface DashboardProps {
	children: ReactNode;
	items: sidebarOptions[];
	theme?: Theme;
}

export function Dashboard({ children, items, theme = "blue" }: DashboardProps) {
	const { isOpen, open, close } = useDisclosure();
	return (
		<div data-theme={theme}>
			<Topbar
				dashboardRoute="/"
				inventoryRoute="#"
				reportsRoute="#"
				onMenuClick={open}
			/>
			<Sidebar isOpen={isOpen} onClose={close} items={items} />
			<Overlay isVisible={isOpen} onClick={close} />
			<main>{children}</main>
		</div>
	);
}
