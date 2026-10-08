import type { ChangeEvent } from "react";

interface SearchbarProps {
	placeholder: string;
	onSubmit: (event: ChangeEvent<HTMLFormElement>) => void;
}

export function Searchbar({ onSubmit, placeholder }: SearchbarProps) {
	return (
		<form
			onSubmit={onSubmit}
			className="flex w-4/5 max-w-3xl rounded-full border-0 bg-white p-2 shadow-sm"
		>
			<input
				type="search"
				name="search"
				placeholder={placeholder}
				aria-label="Buscador"
				className="grow border-transparent bg-transparent px-6 py-2 text-base outline-none"
			/>
			<button
				type="submit"
				className="cursor-pointer rounded-full border-2 bg-(--accent) px-6 py-0 font-bold text-white transition-(--transition) hover:border-(--accent) hover:bg-white hover:text-(--accent)"
			>
				Buscar
			</button>
		</form>
	);
}
