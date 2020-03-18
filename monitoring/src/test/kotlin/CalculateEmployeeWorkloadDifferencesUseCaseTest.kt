import org.junit.Test
import ru.fors.entity.MAXIMUM_WORKLOAD_WORKING_HOURS
import ru.fors.entity.workload.WorkUnit
import ru.fors.monitoring.api.domain.usecase.CalculateEmployeeWorkloadDifferencesUseCase
import ru.fors.monitoring.domain.usecase.CalculateEmployeeWorkloadDifferencesUseCaseImpl
import java.time.LocalDate
import com.google.common.truth.Truth.assertThat
import ru.fors.entity.workload.monitoring.WorkloadDifferenceType

class CalculateEmployeeWorkloadDifferencesUseCaseTest {

    private val usecase: CalculateEmployeeWorkloadDifferencesUseCase = CalculateEmployeeWorkloadDifferencesUseCaseImpl()

    @Test
    fun `test overload work units`() {
        val workloadDiffList = usecase.execute(genWorkUnits(MAXIMUM_WORKLOAD_WORKING_HOURS + 2))
        assertThat(workloadDiffList).hasSize(1)
        val workloadDiff = workloadDiffList.first()
        assertThat(workloadDiff.date).isEqualTo(LocalDate.now())
        assertThat(workloadDiff.type).isEqualTo(WorkloadDifferenceType.OVERLOAD)
        assertThat(workloadDiff.difference).isEqualTo(2)
    }

    @Test
    fun `test idle work units`() {
        val workloadDiffList = usecase.execute(genWorkUnits(MAXIMUM_WORKLOAD_WORKING_HOURS - 6))
        assertThat(workloadDiffList).hasSize(1)
        val workloadDiff = workloadDiffList.first()
        assertThat(workloadDiff.date).isEqualTo(LocalDate.now())
        assertThat(workloadDiff.type).isEqualTo(WorkloadDifferenceType.IDLE)
        assertThat(workloadDiff.difference).isEqualTo(6)
    }

    @Test
    fun `test correct work units`() {
        val workloadDiffList = usecase.execute(genWorkUnits(MAXIMUM_WORKLOAD_WORKING_HOURS))
        assertThat(workloadDiffList).hasSize(1)
        val workloadDiff = workloadDiffList.first()
        assertThat(workloadDiff.date).isEqualTo(LocalDate.now())
        assertThat(workloadDiff.type).isEqualTo(WorkloadDifferenceType.NONE)
        assertThat(workloadDiff.difference).isEqualTo(0)
    }

    private fun genWorkUnits(hours: Int): List<WorkUnit> {
        val date = LocalDate.now()
        return (0 until hours).map { WorkUnit(date = date, hours = 1) }
    }
}