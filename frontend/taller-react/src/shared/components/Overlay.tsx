import { zIndex } from "@utils/GlobalVar";

interface OverlayProps {
	isVisible: boolean;
	onClick: () => void;
}

export function Overlay({ isVisible, onClick }: OverlayProps) {
	const display = isVisible ? "" : "hidden";

	return (
		<button
			type="button"
			aria-label="close sidebar"
			onClick={onClick}
			className={`fixed inset-0 ${zIndex.Overlay} block bg-black/60 transition delay-75 ease-in-out ${display}`}
			inert={!isVisible}
		></button>
	);
}
