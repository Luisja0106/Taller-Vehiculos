import { zIndex } from "@utils/GlobalVar";

interface OverlayProps {
	isVisible: boolean;
	onClick: () => void;
}

export function Overlay({ isVisible, onClick }: OverlayProps) {
	const opacity = isVisible ? "bg-black/60" : "bg-black/0";

	return (
		<button
			type="button"
			aria-label="close sidebar"
			onClick={onClick}
			className={`fixed inset-0 ${zIndex.Overlay} block ${opacity} transition delay-100`}
			inert={!isVisible}
		></button>
	);
}
