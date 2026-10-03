interface HeroProps {
	gradientClasses: string;
	buttonColor: string;
}

export function Hero({ gradientClasses, buttonColor }: HeroProps) {
	return (
		<section
			className={`flex h-45 w-full flex-col items-center justify-center gap-4 ${gradientClasses}`}
		>
			<form className="flex w-4/5 max-w-3xl rounded-full border-0 bg-white p-2 shadow-sm">
				<input
					type="search"
					placeholder="Buscar por ID, Vehiculo, Empleado"
					className="grow border-transparent bg-transparent px-6 py-2 text-base outline-none"
				/>
				<button
					type="submit"
					className={`cursor-pointer rounded-full border-2 px-6 py-0 font-bold transition-(--transition) ${buttonColor}`}
				>
					Buscar
				</button>
			</form>
		</section>
	);
}
