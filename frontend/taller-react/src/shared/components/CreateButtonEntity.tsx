interface CreateButtonEntityProps {
	value: string;
	onClick: () => void;
}

export function CreatebuttonEntity({
	value,
	onClick,
}: CreateButtonEntityProps) {
	return (
		<button
			type="button"
			onClick={onClick}
			className="transition(--transition) relative w-full cursor-pointer rounded-md border border-transparent border-solid bg-linear-to-r from-(--hero-from) from-20% via-(--hero-via) to-(--hero-to) px-8 py-4 font-medium text-white text-xl shadow-md hover:border-(--accent) hover:bg-linear-to-r hover:from-white hover:via-white hover:to-white hover:text-(--accent)"
		>
			{value}
		</button>
	);
}
