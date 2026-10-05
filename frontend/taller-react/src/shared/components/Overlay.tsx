interface OverlayProps {
	onClick: () => void;
}

export function Overlay({ onClick }: OverlayProps) {
	return (
		<button
			type="button"
			aria-label="close sidebar"
			onClick={onClick}
			className="fixed inset-0 z-99 block bg-black/60"
		></button>
	);
}
