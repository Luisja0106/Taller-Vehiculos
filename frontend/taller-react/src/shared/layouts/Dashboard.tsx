import { TopBar } from "@components/Hero.tsx";
import type { PropsWithChildren } from "react";

export function DashboardLayout({ children }: PropsWithChildren) {
	return (
		<>
			<header className="fixed top-0 z-98 flex h-16 w-full items-center gap-8 bg-white p-6">
				<TopBar />
			</header>
			{children}
		</>
	);
}
