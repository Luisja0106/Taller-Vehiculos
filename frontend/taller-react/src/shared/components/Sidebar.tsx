import { Brand } from "@components/Brand";
import { z_index } from "@utils/GlobalVar.ts";
import { Link } from "@utils/Link.tsx";

export interface sidebarOptions {
	label: string;
	href: string;
}
interface SideBarProps {
	isOpen: boolean;
	onClose: () => void;
	items: sidebarOptions[];
}

export function Sidebar({ isOpen, onClose, items }: SideBarProps) {
	const positionClasses = isOpen ? "translate-x-0" : "-translate-x-full";

	return (
		<aside
			className={`fixed top-0 left-0 ${z_index.Sidebar} flex h-lvh w-2xs flex-col gap-8 bg-white p-6 shadow-2xl transition-transform duration-300 ease-in-out ${positionClasses}`}
			inert={!isOpen}
		>
			<div className="flex items-center justify-between">
				<Brand />
				<button
					type="button"
					className="cursor-pointer text-nowrap border-transparent bg-transparent text-xl"
					aria-label="close-sidebar"
					onClick={onClose}
				>
					X
				</button>
			</div>
			<ul className="ml-2 flex flex-col gap-8">
				{items.map((option) => (
					<li key={option.label}>
						<Link
							href={option.href}
							className="block rounded-md p-2 font-medium text-base text-gray-400 transition-(--transition) hover:bg-gray-400/10 hover:text-black"
						>
							{option.label}
						</Link>
					</li>
				))}
			</ul>
		</aside>
	);
}
